<template>
    <div class="relative mt-20 bg-white">
        <a-list item-layout="horizontal" :data-source="data">
            <template #renderItem="{ item }">
                <a-list-item>
                    <a-list-item-meta
                        description="Ant Design, a design language for background applications, is refined by Ant UED Team">
                        <template #title>
                            <a href="https://www.antdv.com/">{{ item.title }}</a>
                        </template>
                        <template #avatar>
                            <a-avatar src="https://joeschmoe.io/api/v1/random" />
                        </template>
                    </a-list-item-meta>
                </a-list-item>
            </template>
        </a-list>
    </div>
</template>
<script setup>
const data = [
    {
        title: 'News 1',
    },
    {
        title: 'News 2',
    },
    {
        title: 'News 3',
    },
    {
        title: 'News 5',
    },
    {
        title: 'News 6',
    },
    {
        title: 'News 7',
    },
    {
        title: 'News 8',
    },
    {
        title: 'News 9',
    },
    {
        title: 'News 10',
    }
];
</script>