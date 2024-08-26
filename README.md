### /sendMessage
`http://localhost:8080/sendMessage?id=123&message=hi&role=user`

`Hello! How can I assist you today?`

### /sendMessage
`http://localhost:8080/sendMessage?id=123&message=be brief&role=system`

### /clearMessages
`http://localhost:8080/clearMessages?id=123`

### /getMessages
`http://localhost:8080/getMessages?id=123`

`
[ {"role": "user", "content": "hi"}, {"role": "assistant", "content": "Hello! How can I assist you today?"} ]`

### /getModel
`http://localhost:8080/getModel?id=123`

`gpt-4`

### /setModel
`http://localhost:8080/setModel?id=123&model=gpt-4`
- `gpt-4`
- `gpt-4o`
- `gpt-4-turbo`