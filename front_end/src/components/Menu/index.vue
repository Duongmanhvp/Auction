<template>
    <div>
        <div class=" flex fixed left-0 top-1/2 transform -translate-y-1/2 w-64 bg-gray-100 shadow-lg rounded-lg transition-all duration-300 z-20"
            :class="menuCollapsed ? '-left-64' : 'left-0'">
            <a-menu v-model:openKeys="state.openKeys" v-model:selectedKeys="state.selectedKeys" mode="inline"
                :items="items"></a-menu>
            <button @click="toggleMenu"
                class="fixed left-56 top-1/2 transform -translate-y-1/2 w-4 h-12 flex items-center justify-center bg-gray-300 hover:bg-gray-400 rounded-full ml-5 z-30">
                <span v-if="menuCollapsed"></span>
                <span v-else class="mb-1">||</span>
            </button>
        </div>
    </div>

    <div class="z-10">
        <a-card hoverable class="h-80 bg-white shadow-lg rounded-md p-4 mt-36">
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>
    </div>
    <div class="z-10">
        <a-card hoverable class="h-80 bg-[#] shadow-lg rounded-md p-4 mt-4">
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>
    </div>
    <div class="z-10">
        <a-card hoverable class="h-auto bg-white shadow-lg rounded-md p-4 mt-4">
            <template #actions>
                <setting-outlined key="setting" />
                <edit-outlined key="edit" />
                <ellipsis-outlined key="ellipsis" />
            </template>
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>
    </div>
    <div class="z-10">
        <a-card hoverable class="h-auto bg-white shadow-lg rounded-md p-4 mt-4">
            <template #actions>
                <setting-outlined key="setting" />
                <edit-outlined key="edit" />
                <ellipsis-outlined key="ellipsis" />
            </template>
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>
    </div>
</template>

<script setup>
import { reactive, ref, h } from 'vue';
import {
    PieChartOutlined,
    MailOutlined,
    DesktopOutlined,
    InboxOutlined,
    AppstoreOutlined,
} from '@ant-design/icons-vue';

const state = reactive({
    selectedKeys: ['1'],
    openKeys: ['sub1'],
    preOpenKeys: ['sub1'],
});
const items = reactive([
    {
        key: '1',
        icon: () => h(PieChartOutlined),
        label: 'Option 1',
        title: 'Option 1',
    },
    {
        key: '2',
        icon: () => h(DesktopOutlined),
        label: 'Option 2',
        title: 'Option 2',
    },
    {
        key: '3',
        icon: () => h(InboxOutlined),
        label: 'Option 3',
        title: 'Option 3',
    },
    {
        key: 'sub1',
        icon: () => h(MailOutlined),
        label: 'Navigation One',
        title: 'Navigation One',
        children: [
            {
                key: '5',
                label: 'Option 5',
                title: 'Option 5',
            },
            {
                key: '6',
                label: 'Option 6',
                title: 'Option 6',
            },
            {
                key: '7',
                label: 'Option 7',
                title: 'Option 7',
            },
            {
                key: '8',
                label: 'Option 8',
                title: 'Option 8',
            },
        ],
    },
    {
        key: 'sub2',
        icon: () => h(AppstoreOutlined),
        label: 'Navigation Two',
        title: 'Navigation Two',
        children: [
            {
                key: '9',
                label: 'Option 9',
                title: 'Option 9',
            },
            {
                key: '10',
                label: 'Option 10',
                title: 'Option 10',
            },
            {
                key: 'sub3',
                label: 'Submenu',
                title: 'Submenu',
                children: [
                    {
                        key: '11',
                        label: 'Option 11',
                        title: 'Option 11',
                    },
                    {
                        key: '12',
                        label: 'Option 12',
                        title: 'Option 12',
                    },
                ],
            },
        ],
    },
]);

const menuCollapsed = ref(true);
const toggleMenu = () => {
    menuCollapsed.value = !menuCollapsed.value;
};
</script>

<style scoped>
.menu-collapsed {
    left: -16rem;
}
</style>
