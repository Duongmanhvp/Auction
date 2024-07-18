<template>
    <div class="home-page">
        <div class="image-container">
            <div class="image-box">
                <img src="image1.jpg" alt="Image 1">
            </div>
            <div class="image-box">
                <img src="image2.jpg" alt="Image 2">
            </div>
            <div class="image-box">
                <img src="image3.jpg" alt="Image 3">
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HomePage'
}
</script>

<style scoped>
.image-container {
    display: flex;
    justify-content: space-around;
    margin-top: 50px;
}

.image-box {
    width: 200px;
    height: 200px;
    border: 1px solid #ccc;
    display: flex;
    justify-content: center;
    align-items: center;
}

.image-box img {
    max-width: 100%;
    max-height: 100%;
}
</style>