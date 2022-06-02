# HttpParser
HttpParser parses HTTP response header based on the predefined validation rules for status line and headernamevalue pairs, and if valid, shows the details in the format below

HTTP version : 1.0
status : 200
Number of valid headers
Number of invaid headers

If HTTP response header is not validated against the predefined rules for status line, it shows "Invalid status line" and aborts parsing of the remaining headernamevalue pairs.


