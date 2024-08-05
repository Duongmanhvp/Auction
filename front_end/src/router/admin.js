import AdminLayout from "../layouts/AdminLayout.vue";
import AllRequestSession from "../views/admin/allRequestSession/index.vue";
import Product from "../views/home/product/index.vue";
import Session from "../views/home/session/index.vue";
import News from "../views/home/news/index.vue";
import History from "../views/home/history/index.vue";
import Introduction from "../views/home/introduction/index.vue";
import Contact from "../views/home/contact/index.vue";

const adminRoutes = [
    {
        path: "/admin",
        component: AdminLayout,
        meta: { requiresAuth: true, requiresAdmin: true },
        children: [
            {
                path: "requestSession",
                name: "request-session",
                component: AllRequestSession
            },
            {
                path: "product",
                name: "admin-product",
                component: Product
            },
            {
                path: "session",
                name: "admin-session",
                component: Session
            },
            {
                path: "news",
                name: "admin-news",
                component: News
            },
            {
                path: "history",
                name: "admin-history",
                component: History
            },
            {
                path: "introduction",
                name: "admin-introduction",
                component: Introduction
            },
            {
                path: "contact",
                name: "admin-contact",
                component: Contact
            },
        ]
    }
];

export default adminRoutes;
