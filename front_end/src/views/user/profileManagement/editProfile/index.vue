<template>
    <div class="flex mt-8 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <Profile />
        </div>
        <div class="w-4/5 container border-l mx-auto p-10 mt-6">
            <div class="container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        Edit Profile
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <form @submit.prevent="submitProfile" class=" flex px-8 space-x-5">
                    
                    <div class="flex-1">
                        <div class="mb-5">
                            <label class="block text-gray-700">Full Name</label>
                            <input type="text" v-model="profile.full_name" :placeholder=_fullName
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                        <div class="mb-5">
                            <label class="block text-gray-700">Birthday</label>
                            <input type="date" v-model="profile.date_of_birth"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                        <div class="mb-5">
                            <label class="block text-gray-700">Gender</label>
                            <select v-model="profile.gender"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2">
                                <option value="" disabled>Select gender</option>
                                <option value="MALE">MALE</option>
                                <option value="FEMALE">FEMALE</option>
                            </select>
                        </div>

                    </div>
                    <div class="flex-1">
                        <div class="mb-5">
                            <label class="block text-gray-700">Address</label>
                            <input type="text" v-model="profile.address" :placeholder=_address
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                   
                        <div class="mb-5">
                            <label class="block text-gray-700">Phone</label>
                            <input type="text" v-model="profile.phone" :placeholder=_phone
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                        <div class="flex justify-center items-center space-x-6">
                            <button type="submit"
                                class=" flex w-40 bg-teal-300 hover:bg-teal-400 outline-gray-400 shadow-lg text-black font-bold py-2 px-4 rounded">
                                <img src="../../../../assets/icon/send.svg" alt="Confirm" class="w-6 h-6 mr-4 ml-1" />
                                Confirm
                            </button>
                            <button type="submit"
                                class=" flex w-36 bg-slate-200 text-black hover:bg-slate-300 outline-gray-600 shadow-lg font-bold py-2 px-4 rounded">
                                <img src="../../../../assets/icon/cancel.svg" alt="Cancel" class="w-6 h-6 mr-4" />
                                Cancel
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <TheChevron />
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import TheChevron from '../../../../components/Chevron/index.vue';
import Profile from '../../../../components/Profile/index.vue';
import { useStore } from 'vuex'


const store = useStore();
const profile = reactive({
    full_name: '',
    date_of_birth: '',
    gender: '',
    address: '',
    avatar: '',
    phone: ''
});

const _fullName = ref('');
const _phone = ref('');
const _address = ref('');

const user = store.getters.getUser;

if (user) {
    _fullName.value = user.fullName;
    _phone.value = user.phone;
    _address.value = user.address;
}


const submitProfile = async () => {
    console.log('profile:', profile);
    const filteredProfile = Object.fromEntries(
        Object.entries(profile).filter(([key, value]) => value !== '')
    );
    console.log('filteredProfile:', filteredProfile);
    try {
        const response = await store.dispatch('updateMyInfo', filteredProfile);
        store.getters.getUser;
    } catch (error) {
        console.error('Error updating profile:', error);
    }
};


</script>