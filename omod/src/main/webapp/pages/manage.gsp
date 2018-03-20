<!DOCTYPE html>
<html>
<head>
    <title>Reporting2</title>
    <link rel="shortcut icon" type="image/ico" href="/${ui.contextPath()}/images/openmrs-favicon.ico"/>
    <link rel="icon" type="image/png\" href="/${ui.contextPath()}/images/openmrs-favicon.png"/>
</head>

<body>
<div id="app">
    <p>{{ message }}</p>
    <button v-on:click="reverseMessage">Reverse Message</button>
</div>
<script src="https://cdn.jsdelivr.net/npm/vue"></script>

<script>
    var app = new Vue({
        el: '#app',
        data: {
            todos: [
                {text: 'Learn JavaScript'},
                {text: 'Learn Vue'},
                {text: 'Build something awesome'}
            ],
            message: 'Hello Vue.js!'
        },
        methods: {
            reverseMessage: function () {
                this.message = this.message.split('').reverse().join('')
            }
        }
    })
</script>
</body>
</html>
