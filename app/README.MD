# Rich Text Editor

This is a simple rich text editor for android applications.

This is created using webview and [summernote](https://summernote.org/)

### Implementation

- `summernote` is locally stored into the `assets` directory of the application.
- A HTML page is created with name `main.html` . This page contains implementation of `summernote` JS.
- We are using WebView to load this page into the application. (check `SummernoteActivity.kt`)
- We are overriding the `WebChromeClient` of browser to open the image picker intent.

### Customisation

- Refer to the official `summernote` [documentation](https://summernote.org/deep-dive/) for toolbar options and styling. 
