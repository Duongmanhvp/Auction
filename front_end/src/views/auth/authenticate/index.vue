<template>

    <div class="bg-cover bg-center bg-fixed min-h-screen flex items-center justify-center"
        :style="{ backgroundImage: `url('${backgroundImage}')` }">
        <router-view />
    </div>

</template>

<script>

export default {
    name: 'Background',
    props: {
        backgroundImage: {
            type: String,
            default: '../assets/bgAuction.jpg'
        }
    }
}

</script>
