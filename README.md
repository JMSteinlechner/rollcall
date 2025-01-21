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
