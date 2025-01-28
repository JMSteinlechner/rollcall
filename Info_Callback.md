# Information regarding implementation of Callback

Dieses Dokument beschreibt, was ich bis jetzt gemacht habe. Um eine Klasse aus dem Rollcall 
modul erfolgreich zu Importieren, muss die Version (23-SNAMPSHOT) in der dependency in 
`pom.xml` definiert werden (weiß nicht warum es nur dann geht).

## Lösungsansatz 1:

Ich wollte Dependency Injection mit einem klassischen Plain Old Java Object machen, hierfür wurden folgende Änderungen vorgenommen:
```java
// bbb-tool/impl/src/java/org/sakaiproject/bbb/impl/BBBAPI.java

// LoC 59
import org.sakaiproject.rollcall.tool.AttendanceCallbackController;

// Callback to Rollcall LoC 108
@Resource
private AttendanceCallbackController attendanceCallbackController;

// LoC 374
// Call the rollcall callback
attendanceCallbackController.handleCallback(meetingID);
```

Ein `AttendanceCallbackController` Object wird dem Code hinzugefügt, welches dann die `handleCallback` Methode aufruft, um die Attendance zu tracken.

```java
// rollcall/tool/src/java/org/sakaiproject/rollcall/tool/AttendanceCallbackController

@Component
public class AttendanceCallbackController {


    public void handleCallback(String meetingID) {
        // TODO: implement callback when meeting ends
        System.out.println("AttendanceCallbackController handleCallback");
    }
}
```

Die Klasse wurde als Spring Component annotiert, um eine simple Dependency Injection zu ermöglichen.

**Aus irgend einem Grund buildet bbb-tool nicht mehr nach diesen Änderungen**

um das Problem zu beheben, habe ich einen Lösungsansatz mit BBBAPI als springbean und einem EventPublisher versucht

## Lösungsansatz 2:

Die Injection wird jetzt mittels Field Injection und Spring durchgeführt 
(nicht umbedingt best practice, es wäre glaube ich besser den Konstruktor zu injectien, wir haben
jedoch keine Kontrolle über den Konstruktor von BBBAPI). Folgende Änderunge wurden durchgeführt:

```java
// bbb-tool/impl/src/java/org/sakaiproject/bbb/impl/BBBAPI.java

// imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

// LoC 79
@Component
public class BaseBBBAPI implements BBBAPI {...}

// LoC 112
// Callback to Rollcall
@Autowired
private AttendanceCallbackController attendanceCallbackController;
@Autowired
private ApplicationEventPublisher eventPublisher;

// LoC 380
// Call the rollcall callback
eventPublisher.publishEvent(this.getMeetingInfo(meetingID, password));
```

Die Idee hier ist, dass der ApplicationEventPublisher ein Event veröffentlicht, auf welches Im 
`AttendanceCallbackController` gelistened wird. In diesem Event befindet sich der `return` value 
der methode `getMeetingInfo` von BBBAPI selbst. Dies ist eine `Map<String, Meeting>`.

```java
// rollcall/tool/src/java/org/sakaiproject/rollcall/tool/AttendanceCallbackController

// new imports
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class AttendanceCallbackController {
    
    @EventListener
    public void handleCallback(Map<String, Object> meetingInfo) {
        // TODO: implement callback when meeting ends
        System.out.println("AttendanceCallbackController handleCallback");
    }
}
```

die `handleCallback` Methode ist als EventListener registriert. Die Funktion HandleCallback 
die MeetingInfo.

**BigBlueButton build failt auch hier. Rollcall buildet**

# Latest developments

Dependency Injection is complete, however, the callback apperently isn't called in the endMeeting method of BaseBBBAPI