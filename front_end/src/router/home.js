import PublicLayout from "../layouts/PublicLayout.vue";
// import Users from "../pages/home/users/index.vue";
import Default from "../pages/home/default/index.vue";
import Product from "../pages/home/product/index.vue";
import Session from "../pages/home/session/index.vue";
import History from "../pages/home/history/index.vue";
import News from "../pages/home/news/index.vue";
import Introduction from "../pages/home/introduction/index.vue";
import Contact from "../pages/home/contact/index.vue";
import AuthenticatedLayout from "../layouts/AuthenticatedLayout.vue";
// import Settings from "../pages/home/settings/index.vue";
import Authenticate from "../views/auth/authenticate/index.vue";
import Login from "../views/auth/login/index.vue";
import Register from "../views/auth/register/index.vue";
import ForgotPassword from "../views/auth/forgotPassword/index.vue";
import Verify from "../views/auth/verify/index.vue";
import Verify2 from "../views/auth/verify2/index.vue";
import ProductManagement from "../views/user/productManagement/index.vue";
import AddProduct from "../pages/user/addProduct/index.vue";


const routes = [
    {
        path: '/',
        redirect: '/home/default'
    },
    {
        path: '/user',
        redirect: '/user/default'
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
                name: "home-home",
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
            // {
            //     path: "users",
            //     name: "home-users",
            //     component: Users
            // },
            // {
            //     path: "settings",
            //     name: "home-settings",
            //     component: Settings
            // },
        ]
    },

    {
        path: "/login",
        component: Authenticate,
        children: [
            {
                path: "",
                name: "login",
                component: Login
            },
            {
                path: "forgotPassword",
                name: "login-forgotPassword",
                component: ForgotPassword,
            },
            {
                path: "verify",
                name: "login-verify",
                component: Verify,
            },
            {
                path: "verify2",
                name: "register-verify",
                component: Verify2,
            },
            {
                path: "/register",
                name: "register",
                component: Register
            },
        ]
    },

    {
        path: "/user",
        component: AuthenticatedLayout,
        children: [
            {
                path: "default",
                name: "user-default",
                component: Default
            },
            {
                path: "addProduct",
                name: "add-product",
                component: AddProduct
            },
            {
                path: "product",
                name: "user-product",
                component: Product
            },
            {
                path: "session",
                name: "user-home",
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
    },
    // {
    //     path: "/productManagement",
    //     component: ProductManagement,
    //     children: [
    //         {
    //             path: "",
    //             name: "add-product",
    //             component: AddProduct
    //         },
    //     ]
    // },
];

export default routes;
