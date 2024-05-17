// spring.security.oauth2.client.registration.github.client-id=10d927a1f54f8ad02940
// spring.security.oauth2.client.registration.github.client-secret=3a2e724f48567d89de6f6250585db3206b348d85
const config = {
    baseURL: 'http://localhost:8081',
    baseURLGitHub: 'https://github.com',
    clientId:'10d927a1f54f8ad02940',
    redirectUri:'http://localhost:3000',
    scope:'read:user'
};

export default config;