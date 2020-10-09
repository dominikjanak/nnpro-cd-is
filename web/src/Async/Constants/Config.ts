const isLocalhost = window.location.host.includes('localhost:')


export default {
    apiUrl: isLocalhost ? 'http://localhost:8080/api/' : 'https://qgenerator.herokuapp.com/api/'  // TODO: Set API url.
}