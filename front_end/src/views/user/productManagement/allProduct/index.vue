<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <MenuProductManagement />
        </div>
        <div class="w-4/5 container border-l bg-blue-100 mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="relative w-full max-w-md mx-auto">
                <h1 class="text-2xl font-bold text-center text-gray-800">
                    All Product
                </h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
            </div>

        </div>
    </div>

    <TheChevron />
</template>

<script setup>
import MenuProductManagement from '../../../../components/MenuProductManagement/index.vue';
import TheChevron from '../../../../components/Chevron/index.vue';

</script>
