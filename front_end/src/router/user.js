import AuthenticatedLayout from "../layouts/AuthenticatedLayout.vue";
import Default from "../views/home/default/index.vue";
import AllSession from "../views/user/sessionManagement/allSession/index.vue";
import AddSession from "../views/user/sessionManagement/addSession/index.vue";
import DeleteSession from "../views/user/sessionManagement/deleteSession/index.vue";
import AllProduct from "../views/user/productManagement/allProduct/index.vue";
import AddProduct from "../views/user/productManagement/addProduct/index.vue";
import DeleteProduct from "../views/user/productManagement/deleteProduct/index.vue";
import Product from "../views/home/product/index.vue";
import Session from "../views/home/session/index.vue";
import News from "../views/home/news/index.vue";
import History from "../views/home/history/index.vue";
import Introduction from "../views/home/introduction/index.vue";
import Contact from "../views/home/contact/index.vue";

const userRoutes = [
    {
        path: "/user",
        component: AuthenticatedLayout,
        meta: { requiresAuth: true },
        children: [
            {
                path: "default",
                name: "user-default",
                component: Default
            },
            {
                path: "allSession",
                name: "all-session",
                component: AllSession
            },
            {
                path: "addSession",
                name: "add-session",
                component: AddSession
            },
            {
                path: "deleteSession",
                name: "delete-session",
                component: DeleteSession
            },
            {
                path: "allProduct",
                name: "all-product",
                component: AllProduct
            },
            {
                path: "addProduct",
                name: "add-product",
                component: AddProduct
            },
            {
                path: "deleteProduct",
                name: "delete-product",
                component: DeleteProduct
            },
            {
                path: "product",
                name: "user-product",
                component: Product
            },
            {
                path: "session",
                name: "user-session",
                component: Session
            },
            {
                path: "news",
                name: "user-news",
                component: News
            },
            {
                path: "history",
                name: "user-history",
                component: History
            },
            {
                path: "introduction",
                name: "user-introduction",
                component: Introduction
            },
            {
                path: "contact",
                name: "user-contact",
                component: Contact
            },
        ]
    }
];

export default userRoutes;
