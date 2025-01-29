# Rollcall Sakai Tool

# Interesting stuff we found out..

## How to define an icon for the tool ? by Jakob S.

After an extensive search and countless tries i must admit there is no easy way to set the tools icon (the icon with which it shows up in the left toolbar) directly in the custom tools project. I did not find any documentation regarding this in the sakai wikis and also could not find a solution myself.

You might be wondering: How do the other non native tools for sakai manage this ?

Well the icon for them is directly set in the sakais base skins `_icons.scss` file. Located at `sakai/library/src/skins/default/src/sass/base/_icons.scss` in the sakai source.

This leaves us with 2 possible solutions to set an icon for a new custom tool.

1. Add 1 line with the icon directly in the _icons.scss like it is done for the tools.
2. Generate / Extend our own sakai skin where we can include our icon.

For this project we are going to do option 1 because it is a quick 1 line change and i dont want to open a new pandoras box with generating skins for Sakai with Morpheus.
More information for Morpheus skin generation can be found here: https://github.com/sakaiproject/sakai/tree/master/library

### Setting our icon

In the `_icons.scss` file we add the line `sakai-rollcall : bi-stopwatch` because our tools name is `sakai.rollcall` (defined in the `sakai.rollcall.xml`  <tool id="sakai.rollcall" ...)

And after a few more hours testing we find out the reason the icon is not showing up in sakai even though we did everything identical to every other tool was having wicket in development mode instead of deployment mode. (`web.xml` file)

```xml
<context-param>
            <param-name>configuration</param-name>
            <!--<param-value>development</param-value>-->
            <param-value>deployment</param-value>
	</context-param>
```

Not sure if all the icont in the bootstrap 1.9 version work (tried with stopwatch.. didnt .. tried with clock worked).

Might be necessary to close web browser to clear all caches from previous session even after rebuilding complete sakai with docker.

## Getting tabs enabled/disabled by Jakob S.

After countless hours of trying, switching out parts of the code with code from the gradebook tool which is also based on wicket and where enable/disable of tabs works, we found a solution.

We still dont know how the gradebook tool makes it work (maybe the older wicket 6 version is the reason) but we wrote some additional new code which fixes the issue.

In the Basepage.java the function disableLink handles the logic.

In all the other tools it looks like this:

```java
protected final void disableLink(final Linkvoid l) {
l.add(new AttributeAppender("class", new Modelstring("current"), " "));
l.replace(new Label("screenreaderlabel", getString("link.screenreader.tabselected")));
l.setEnabled(false);
}
```

But this does not work for our tool (and the attendance tool) for a reason i cannot explain. In the sakai native tools this function changes a link tag to a span tag and applies the current class which enables it. In our tool it just applies the class but does not change the tag and therefore also does not make it look enabled.

Our code makes it work like this

```java
protected final void disableLink(final Link<Void> l) {
		/*
		 * since the disable does not apply correctly to the disabled link we need to transform the <a> into a span
		 * the base code from other tools and sakai navtive tools for disable link didnt work
		 * it works not with the add new behaviour function
		 */
		l.add(new AttributeAppender("class", Model.of("current"), " "));

		// Replace the label inside if you have a <span wicket:id="screenreaderlabel"> in your markup
		l.replace(new Label("screenreaderlabel", getString("link.screenreader.tabselected")));

		// Add a Behavior that, when the link is disabled, changes <a> to <span> and removes href
		l.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag) {
				super.onComponentTag(component, tag);

				// Only transform if the link is actually disabled at render-time
				if (!component.isEnabledInHierarchy()) {
					// Turn <a> into <span>
					tag.setName("span");
					// Remove the href attribute altogether
					tag.remove("href");
				}
			}
		});

		// Finally, mark the link as disabled
		l.setEnabled(false);

	}
```
