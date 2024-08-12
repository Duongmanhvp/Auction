<template>
    <div class="flex mt-8 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <Profile />
        </div>

        <div class="w-4/5 container border-l mx-auto p-10 mt-12">
            <a-card hoverable class=" w-full h-auto bg-white shadow-lg rounded-lg p-4">
                <template #cover>
                    <img src="../../../../assets/icon/avatar.svg" alt="Avatar" class="w-40 h-40 mr-4 ml-1" />
                </template>
                <a-tabs v-model:activeKey="activeKey" centered>
                    <a-tab-pane key="1" tab="Personal Informations">
                        <div class="text-xl ml-20 space-y-5">
                            <a-card-meta title="Full Name" :description="fullName">
                            </a-card-meta>
                            <a-card-meta title="Birthday" :description="birthday">
                            </a-card-meta>
                            <a-card-meta title="Gender" :description="gender">
                            </a-card-meta>
                        </div>
                    </a-tab-pane>
                    <a-tab-pane key="2" tab="Contact Informations" force-render>
                        <div class="text-xl ml-20 space-y-5">
                            <a-card-meta title="Email" :description="email">
                            </a-card-meta>
                            <a-card-meta title="Address" :description="address">
                            </a-card-meta>
                            <a-card-meta title="Phone Number" :description="phone">
                            </a-card-meta>
                        </div>

                    </a-tab-pane>
                </a-tabs>
            </a-card>
            
            <TheChevron />
        </div>
    </div>
</template>

<script setup>
import TheChevron from '../../../../components/Chevron/index.vue';
import Profile from '../../../../components/Profile/index.vue';
import { ref } from 'vue';
import { message } from 'ant-design-vue';



import { useStore } from "vuex";
const activeKey = ref('1');
const fullName = ref('');
const birthday = ref('');
const phone = ref('');
const email = ref('');
const address = ref('');
const gender = ref('');

const store = useStore();

const user = store.getters.getUser;

if (user) {
    fullName.value = user[0];
    birthday.value = user[1];
    email.value = user[2];
    phone.value = user[3];
    address.value = user[4];
    gender.value = user[5];
}

</script>
