<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <MenuSessionManagement />
        </div>
        <div class="w-4/5 container border-l bg-blue-100 mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="relative w-full">
                <h1 class="text-2xl font-bold text-center text-gray-800">
                    Session Requests
                </h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                <a-table :columns="columns" :data-source="data" :row-key="record => record.id">
                    <template #bodyCell="{ column, record }">
                        <template v-if="column.key === 'actions'">
                            <span>
                                <a @click="approveSession(record.id)">Approve</a>
                                <a-divider type="vertical" />
                                <a @click="rejectSession(record.id)">Reject</a>
                            </span>
                        </template>
                    </template>
                </a-table>
            </div>
        </div>
    </div>

    <TheChevron />
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import MenuSessionManagement from '../../../components/MenuSessionManagement/index.vue';
import TheChevron from '../../../components/Chevron/index.vue';
import baseService from '../../../services/base-service';

const columns = ref([
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Title',
        dataIndex: 'title',
        key: 'title',
    },
    {
        title: 'Description',
        dataIndex: 'description',
        key: 'description',
    },
    {
        title: 'Created At',
        dataIndex: 'createdAt',
        key: 'createdAt',
    },
    {
        title: 'Confirm Date',
        dataIndex: 'confirmDate',
        key: 'confirmDate',
    },
    {
        title: 'End Registration',
        dataIndex: 'endRegistration',
        key: 'endRegistration',
    },
    {
        title: 'Start Time',
        dataIndex: 'startTime',
        key: 'startTime',
    },
    {
        title: 'End Time',
        dataIndex: 'endTime',
        key: 'endTime',
    },
    {
        title: 'Start Bid',
        dataIndex: 'startBid',
        key: 'startBid',
    },
    {
        title: 'Price Per Step',
        dataIndex: 'pricePerStep',
        key: 'pricePerStep',
    },
    {
        title: 'End Bid',
        dataIndex: 'endBid',
        key: 'endBid',
    },
    {
        title: 'Status',
        dataIndex: 'status',
        key: 'status',
    },
    {
        title: 'Actions',
        key: 'actions',
    },
]);

const data = ref([]);

const fetchSessions = async () => {
    try {
        const response = await baseService.get('/admin/sessions');
        data.value = response.data;
    } catch (error) {
        console.error('Failed to fetch sessions:', error);
    }
};

const approveSession = async (id) => {
    try {
        await baseService.post(`/admin/sessions/${id}/approve`);
        message.success('Session approved successfully');
        fetchSessions();
    } catch (error) {
        message.error('Failed to approve session');
    }
};

const rejectSession = async (id) => {
    try {
        await baseService.post(`/admin/sessions/${id}/reject`);
        message.success('Session rejected successfully');
        fetchSessions();
    } catch (error) {
        message.error('Failed to reject session');
    }
};

onMounted(fetchSessions);
</script>
