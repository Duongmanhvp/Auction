<template>
    <div class="relative mt-12">
        <a-carousel autoplay class="mr-4 mb-4" ref="carouselRef">
            <div class="relative h-72">
                <img src="../../../assets/image1.jpg" alt="Auction 1" class="w-full h-full object-cover" />
                <div class="absolute bottom-0 left-0 w-full bg-black bg-opacity-50 text-white p-4">
                    <h3 class="text-lg font-bold">Online Auction</h3>
                    <p class="text-sm">The community is strong, the opportunities are endless, total victory for
                        everyone.
                    </p>
                </div>
            </div>
            <div class="relative h-72">
                <img src="../../../assets/image2.jpg" alt="Auction 2" class="w-full h-full object-cover" />
                <div class="absolute bottom-0 left-0 w-full bg-black bg-opacity-50 text-white p-4">
                    <h3 class="text-lg font-bold">Safe Auction</h3>
                    <p class="text-sm">Ensuring fair competition and the most transparent results.</p>
                </div>
            </div>
            <div class="relative h-72">
                <img src="../../../assets/image3.jpg" alt="Auction 3" class="w-full h-full object-cover" />
                <div class="absolute bottom-0 left-0 w-full bg-black bg-opacity-50 text-white p-4">
                    <h3 class="text-lg font-bold">Convenient Auction</h3>
                    <p class="text-sm">Simple auction, quick ownership of assets upon successful auction.</p>
                </div>
            </div>
            <div class="relative h-72">
                <img src="../../../assets/image4.jpg" alt="Auction 4" class="w-full h-full object-cover" />
                <div class="absolute bottom-0 left-0 w-full bg-black bg-opacity-50 text-white p-4">
                    <h3 class="text-lg font-bold">Auction Anytime Anywhere</h3>
                    <p class="text-sm">Full updates on asset types, auctions are held 24/7</p>
                </div>
            </div>
        </a-carousel>

        <button @click="prevSlide"
            class="absolute left-2 top-1/2 transform -translate-y-1/2 bg-white bg-opacity-50 p-2 rounded-full">
            <img src="../../../assets/icon/prev-arrow-slide.svg" alt="Previous" class="w-6 h-6" />
        </button>
        <button @click="nextSlide"
            class="absolute right-2 top-1/2 transform -translate-y-1/2 bg-white bg-opacity-50 p-2 mr-4 rounded-full">
            <img src="../../../assets/icon/next-arrow-slide.svg" alt="Next" class="w-6 h-6" />
        </button>
    </div>

    <div class="relative w-full max-w-md mx-auto mt-6">
        <h1 class="text-2xl font-bold text-center text-gray-800">
            Most Popular Products
        </h1>
        <div class="border-b-2 border-blue-200 my-4"></div>
    </div>

    <div class="flex space-x-4 mt-4 mr-4">

        <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
            <template #cover>
                <img alt="example" src="../../../assets/product.jpg" />
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
                <img alt="example" src="../../../assets/product.jpg" />
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
                <img alt="example" src="../../../assets/product.jpg" />
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
                <img alt="example" src="../../../assets/product.jpg" />
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

    <TheCard />
    <TheChevron />
</template>

<script>
import TheCard from '../../../components/Card/index.vue';
import TheChevron from '../../../components/Chevron/index.vue';
import { ref } from 'vue';

export default {
    components: {
        TheCard,
        TheChevron
    },
    setup() {
        const carouselRef = ref(null);

        const prevSlide = () => {
            if (carouselRef.value) {
                carouselRef.value.prev();
            }
        };

        const nextSlide = () => {
            if (carouselRef.value) {
                carouselRef.value.next();
            }
        };

        return {
            carouselRef,
            prevSlide,
            nextSlide,
        };
    },
};
</script>

<style scoped>
:deep(.slick-slide) {
    text-align: center;
    height: 300px;
    line-height: 300px;
    overflow: hidden;
}

:deep(.slick-slide h3) {
    color: #fff;
}
</style>
