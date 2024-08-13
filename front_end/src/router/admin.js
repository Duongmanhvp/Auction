import AdminLayout from "../layouts/AdminLayout.vue";
import AuctionManagement from "../views/admin/auctionManagement/index.vue";
import AuctionSessionManagement from "../views/admin/auctionSessionManagement/index.vue";
import ProductManagement from "../views/admin/productManagement/index.vue";
import UserManagement from "../views/admin/userManagement/index.vue";
import ProfileManagement from "../views/admin/profileManagement/profile/index.vue";
import Profile from "../views/admin/profileManagement/profile/index.vue";
import EditProfile from "../views/admin/profileManagement/editProfile/index.vue";
import ChangePassword from "../views/admin/profileManagement/changePassword/index.vue";

const adminRoutes = [
    {
        path: "/admin",
        component: AdminLayout,
        meta: { requiresAuth: true, requiresAdmin: true },
        children: [
            {
                path: "auctionManagement",
                name: "auction-management",
                component: AuctionManagement
            },
            {
                path: "auctionSessionManagement",
                name: "auction-session-management",
                component: AuctionSessionManagement
            },
            {
                path: "productManagement",
                name: "product-management",
                component: ProductManagement
            },
            {
                path: "userManagement",
                name: "user-management",
                component: UserManagement
            },
            {
                path: "profileManagement",
                name: "profile-management",
                component: ProfileManagement
            },
            {
                path: "profile",
                name: "admin-profile",
                component: Profile
            },
            {
                path: "editProfile",
                name: "admin-edit-profile",
                component: EditProfile
            },
            {
                path: "changePassword",
                name: "admin-changePassword",
                component: ChangePassword,
                meta: { requiresVerification: true }
            },
        ]
    }
];

export default adminRoutes;
