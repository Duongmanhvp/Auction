<template>
    <div class="relative w-full max-w-md mx-auto mt-6">
        <h1 class="text-2xl font-bold text-center text-gray-800">
            Title
        </h1>
        <div class="border-b-2 border-blue-200 my-4"></div>
    </div>

    <div class="flex space-x-4 mt-4 mr-4">

        <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
            <template #cover>
                <img alt="example" src="../../assets/images/product.jpg" />
            </template>
            <template #actions>
            </template>
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>

        <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
            <template #cover>
                <img alt="example" src="../../assets/images/product.jpg" />
            </template>
            <template #actions>
            </template>
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>

        <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
            <template #cover>
                <img alt="example" src="../../assets/images/product.jpg" />
            </template>
            <template #actions>
            </template>
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>

        <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
            <template #cover>
                <img alt="example" src="../../assets/images/product.jpg" />
            </template>
            <template #actions>
            </template>
            <a-card-meta title="Card title" description="This is the description">
                <template #avatar>
                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                </template>
            </a-card-meta>
        </a-card>
    </div>
</template>