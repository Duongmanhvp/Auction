import { createApp } from 'vue'
import router from './router/index.js'
import App from './App.vue'
import components from './components/index.js';

import 'ant-design-vue/dist/reset.css';
import './index.css'

const app = createApp(App);
app.use(router);
app.mount('#app');

Object.values(components).forEach(component => {
    app.use(component);
});

// app.config.globalProperties.$message = message;