import { createApp } from 'vue'
import router from './router/index.js'
import App from './App.vue'
import components from './components/index.js';
import { createPinia } from 'pinia';

import 'ant-design-vue/dist/reset.css';
import './index.css'

const app = createApp(App);

const pinia = createPinia();

app.use(router);
app.use(pinia);
app.mount('#app');

Object.values(components).forEach(component => {
    app.use(component);
});
