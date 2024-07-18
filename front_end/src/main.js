import { createApp } from 'vue'
import router from './router/index.js'
import App from './App.vue'

import {
    Drawer,
    Button,
    message
} from 'ant-design-vue';

import 'ant-design-vue/dist/reset.css';
import './index.css'

const app = createApp(App);
app.use(router);
app.use(Button);
app.use(Drawer);
app.mount('#app');

app.config.globalProperties.$message = message;