import { createRouter, createWebHistory } from 'vue-router';
import routes from './home.js';

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;