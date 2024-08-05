import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '../stores/auth/auth-store';
import authRoutes from './auth';
import userRoutes from './user';
import adminRoutes from './admin';
import PublicLayout from "../layouts/PublicLayout.vue";
import Default from "../views/home/default/index.vue";
import Product from "../views/home/product/index.vue";
import Session from "../views/home/session/index.vue";
import History from "../views/home/history/index.vue";
import News from "../views/home/news/index.vue";
import Introduction from "../views/home/introduction/index.vue";
import Contact from "../views/home/contact/index.vue";

const routes = [
    {
        path: '/',
        redirect: '/home/default'
    },
    {
        path: "/home",
        component: PublicLayout,
        children: [
            {
                path: "default",
                name: "home-default",
                component: Default
            },
            {
                path: "product",
                name: "home-product",
                component: Product
            },
            {
                path: "session",
                name: "home-session",
                component: Session
            },
            {
                path: "news",
                name: "home-news",
                component: News
            },
            {
                path: "history",
                name: "home-history",
                component: History
            },
            {
                path: "introduction",
                name: "home-introduction",
                component: Introduction
            },
            {
                path: "contact",
                name: "home-contact",
                component: Contact
            },
        ]
    },
    ...authRoutes,
    ...userRoutes,
    ...adminRoutes
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    const { isAuth, isAdmin, email } = authStore;

    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    const requiresAdmin = to.matched.some(record => record.meta.requiresAdmin);
    const requiresVerification = to.matched.some(record => record.meta.requiresVerification);

    if (requiresAuth && !isAuth) {
        next({ path: '/login', query: { redirect: to.fullPath } });
    } else if (requiresAdmin && !isAdmin) {
        next('/user/default');
    } else if (requiresVerification && !email) {
        next({ path: '/login/verify2', query: { redirect: to.fullPath } });
    } else if (isAuth && (to.path === '/login' || to.path === '/register')) {
        next(isAdmin ? '/admin/requestSession' : '/user/default');
    } else if (isAuth && isAdmin && to.path.startsWith('/user')) {
        next('/admin/requestSession');
    } else {
        next();
    }
});

export default router;
