<template>
    <div class="relative mt-12">
        <a-carousel autoplay class="mb-8" ref="carouselRef">
            <div class="relative h-72">
                <img src="../../../assets/images/image1.jpg" alt="Auction 1" class="w-full h-full object-cover" />
                <div class="absolute bottom-0 left-0 w-full bg-black bg-opacity-50 text-white p-4">
                    <h3 class="text-lg font-bold">Online Auction</h3>
                    <p class="text-sm">The community is strong, the opportunities are endless, total victory for
                        everyone.
                    </p>
                </div>
            </div>
            <div class="relative h-72">
                <img src="../../../assets/images/image2.jpg" alt="Auction 2" class="w-full h-full object-cover" />
                <div class="absolute bottom-0 left-0 w-full bg-black bg-opacity-50 text-white p-4">
                    <h3 class="text-lg font-bold">Safe Auction</h3>
                    <p class="text-sm">Ensuring fair competition and the most transparent results.</p>
                </div>
            </div>
            <div class="relative h-72">
                <img src="../../../assets/images/image3.jpg" alt="Auction 3" class="w-full h-full object-cover" />
                <div class="absolute bottom-0 left-0 w-full bg-black bg-opacity-50 text-white p-4">
                    <h3 class="text-lg font-bold">Convenient Auction</h3>
                    <p class="text-sm">Simple auction, quick ownership of assets upon successful auction.</p>
                </div>
            </div>
            <div class="relative h-72">
                <img src="../../../assets/images/image4.jpg" alt="Auction 4" class="w-full h-full object-cover" />
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

    <div class="flex">
        <div class="w-1/5 ml-4 mr-4">
            <div class="z-10">
                <a-card hoverable class="h-40 bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Search</h1>
                    <div class="flex items-center justify-center mt-4">
                        <input type="text" class="w-3/4 p-2 border border-gray-300 rounded-md"
                            placeholder="Enter product keyword...">
                        <button class="ml-2 p-2 bg-blue-50 hover:bg-teal-200 rounded-full outline-gray-400 shadow-lg">
                            <img src="../../../assets/icon/search.svg" alt="Search" class="w-5 h-5" />
                        </button>
                    </div>
                </a-card>
            </div>
            <div class="z-10">
                <a-card hoverable class="h-auto bg-[#] shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold mb-4"> News </h1>
                    <div class="space-y-4">
                        <a-card-meta title="News 1" description="This is the description">
                            <template #avatar>
                                <a-avatar src="https://joeschmoe.io/api/v1/random" />
                            </template>
                        </a-card-meta>
                        <a-card-meta title="News 2" description="This is the description">
                            <template #avatar>
                                <a-avatar src="https://joeschmoe.io/api/v1/random" />
                            </template>
                        </a-card-meta>
                        <a-card-meta title="News 3" description="This is the description">
                            <template #avatar>
                                <a-avatar src="https://joeschmoe.io/api/v1/random" />
                            </template>
                        </a-card-meta>
                        <a-card-meta title="News 4" description="This is the description">
                            <template #avatar>
                                <a-avatar src="https://joeschmoe.io/api/v1/random" />
                            </template>
                        </a-card-meta>
                        <a-card-meta title="News 5" description="This is the description">
                            <template #avatar>
                                <a-avatar src="https://joeschmoe.io/api/v1/random" />
                            </template>
                        </a-card-meta>
                        <a-card-meta title="News 6" description="This is the description">
                            <template #avatar>
                                <a-avatar src="https://joeschmoe.io/api/v1/random" />
                            </template>
                        </a-card-meta>
                    </div>
                </a-card>
            </div>
        </div>

        <div class="w-4/5 rounded-md">
            <div class="max-w-md mx-auto">
                <h1 class="text-2xl font-bold text-center text-gray-800">
                    Most Popular Products
                </h1>
                <div class="border-b-2 border-blue-300 mt-2 mb-6"></div>
            </div>

            <div class="flex space-x-4 ml-6 mr-3">
                <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
                    <template #cover>
                        <img alt="example" src="../../../assets/images/product.jpg" />
                    </template>
                    <template #actions>
                    </template>
                    <a-card-meta title="License Plate" description="10.000.000 VND">
                        <template #avatar>
                            <a-avatar src="https://joeschmoe.io/api/v1/random" />
                        </template>
                    </a-card-meta>
                </a-card>

                <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
                    <template #cover>
                        <img alt="example" src="../../../assets/images/product.jpg" />
                    </template>
                    <template #actions>
                    </template>
                    <a-card-meta title="Art" description="8.888.888 VND">
                        <template #avatar>
                            <a-avatar src="https://joeschmoe.io/api/v1/random" />
                        </template>
                    </a-card-meta>
                </a-card>

                <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
                    <template #cover>
                        <img alt="example" src="../../../assets/images/product.jpg" />
                    </template>
                    <template #actions>
                    </template>
                    <a-card-meta title="Vehicles" description="35.600.000 VND">
                        <template #avatar>
                            <a-avatar src="https://joeschmoe.io/api/v1/random" />
                        </template>
                    </a-card-meta>
                </a-card>

                <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
                    <template #cover>
                        <img alt="example" src="../../../assets/images/product.jpg" />
                    </template>
                    <template #actions>
                    </template>
                    <a-card-meta title="Antiques" description="9.999.000.000 VND">
                        <template #avatar>
                            <a-avatar src="https://joeschmoe.io/api/v1/random" />
                        </template>
                    </a-card-meta>
                </a-card>
            </div>
            <div class="max-w-md mx-auto mt-14">
                <h1 class="text-2xl font-bold text-center text-gray-800">
                    New Products
                </h1>
                <div class="border-b-2 border-blue-300 mt-2 mb-6"></div>
            </div>

            <div class="flex space-x-4 ml-6 mr-3">
                <a-card hoverable class="w-1/4 h-auto bg-white shadow-lg rounded-lg p-4">
                    <template #cover>
                        <img alt="example" src="../../../assets/images/product.jpg" />
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
                        <img alt="example" src="../../../assets/images/product.jpg" />
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
                        <img alt="example" src="../../../assets/images/product.jpg" />
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
                        <img alt="example" src="../../../assets/images/product.jpg" />
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

            <TheChevron />
        </div>
    </div>
</template>

<script>
import TheChevron from '../../../components/Chevron/index.vue';
import { ref } from 'vue';

export default {
    components: {
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
