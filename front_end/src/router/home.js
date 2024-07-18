const home = [
    {
        path: "/",
        component: () => import("../components/TheHeader.vue"),
    },
    {
        path: "/login",
        component: () => import("../views/Login.vue"),
    },
    {
        path: "/login/register",
        component: () => import("../views/Register.vue"),
    },
    {
        path: "/login/forgotPassword",
        component: () => import("../views/ForgotPassword.vue"),
    },
    {
        path: "/login/Verify",
        component: () => import("../views/Verify.vue"),
    },
];

export default home;