<template>
    <div class="relative mt-20">
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
        title: 'Ant Design Title 1',
    },
    {
        title: 'Ant Design Title 2',
    },
    {
        title: 'Ant Design Title 3',
    },
    {
        title: 'Ant Design Title 5',
    },
    {
        title: 'Ant Design Title 6',
    },
    {
        title: 'Ant Design Title 7',
    },
    {
        title: 'Ant Design Title 8',
    },
    {
        title: 'Ant Design Title 9',
    },
    {
        title: 'Ant Design Title 10',
    }
];
</script>