<template>
  <div class="relative bg-white mt-20">
    <a-list item-layout="horizontal" :data-source="data">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-list-item-meta :description="item.description">
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
    title: 'Historic Art Auction Breaks Records',
    description: 'A Van Gogh painting sells for a record-breaking $150 million at Sotheby'
  },
  {
    title: 'Rare Diamond Fetches High Price',
    description: 'A 50-carat blue diamond sells for $45 million in a heated bidding war.'
  },
  {
    title: 'World’s Largest Online Auction',
    description: 'Over 10,000 items, including rare antiques, are auctioned off in a 24-hour online event.'
  },
  {
    title: 'Celebrity Memorabilia Auctioned for Charity',
    description: "Items from Hollywood stars raise $5 million for children’s hospitals."
  },
  {
    title: 'Ancient Artifact Unearthed and Sold',
    description: 'A rare Egyptian artifact from 2000 BC sells for $10 million at an exclusive auction.'
  },
  {
    title: 'Tech Startup Auction Nets $1 Billion',
    description: 'Investors compete in a high-stakes auction for shares in a breakthrough tech company.'
  },
  {
    title: 'Auction for World’s Most Expensive Wine',
    description: 'A bottle of 1945 Romanée-Conti wine sells for $500,000 at auction.'
  },
  {
    title: 'Sports Memorabilia Auction Draws Record Bidders',
    description: 'Michael Jordan’s rookie jersey sells for $1.2 million in a competitive auction.'
  },
  {
    title: 'Luxury Car Auction Features Classic Ferraris',
    description: 'A 1962 Ferrari 250 GTO sells for $48 million, setting a new auction record.'
  },
  {
    title: 'Charity Auction Raises Millions for Conservation',
    description: 'Rare wildlife photography and experiences raise $8 million for conservation efforts.'
  }
];
</script>
