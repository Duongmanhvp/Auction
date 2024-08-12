import { createApp } from 'vue'
import router from './router/index.js'
import App from './App.vue'
import components from './components/index.js';
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persistedstate';

import 'ant-design-vue/dist/reset.css';
import './assets/css/tailwind.css';

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPersist);
app.use(pinia);
app.use(router);
app.mount('#app');

Object.values(components).forEach(component => {
    app.use(component);
});
