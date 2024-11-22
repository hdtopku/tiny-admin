<template>
  <a-drawer
      :title="product?.productName || 'Product Preview'"
      placement="left"
      :visible.sync="visible"
      @close="visible = false"
      :size="width < 768 ? 'default' : 'large'"
      :bodyStyle="{ padding: '0px 16px' }"
  >
    <template #title>
      <ToolTip :content="product?.productName || 'Product Preview'"/>
    </template>
    <div>
      <!-- Fixed carousel section -->
      <div class="fixed-carousel grid grid-cols-1 justify-center text-center">
        <ImageCarousel class="mx-auto" :width="'200px'" :img-urls="product?.album || []"/>
        <a-divider style="margin: 16px 0;">Product Details</a-divider>
      </div>

      <!-- Scrollable content for product details -->
      <div class="scrollable-content">
        <a-descriptions bordered column="1" style="margin-top: 16px;">
          <a-descriptions-item label="Description">
            {{ product.productDesc || "No description available" }}
          </a-descriptions-item>
          <a-descriptions-item label="Market Price">
            ${{ product.marketPrice }}
          </a-descriptions-item>
          <a-descriptions-item label="Sale Price">
            ${{ product.salePrice }}
          </a-descriptions-item>
          <a-descriptions-item label="Stock">{{ product.stock }}</a-descriptions-item>
        </a-descriptions>

        <!-- Additional product details -->
        <div v-if="product.detailHtml" class="product-detail">
          <h3>Product Details</h3>
          <div v-html="product.detailHtml"/>
        </div>
      </div>
    </div>
  </a-drawer>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import ImageCarousel from "@/views/pms/product/ImageCarousel.vue";
import {useWindowSize} from "@vueuse/core";

// Get the current window width
const {width} = useWindowSize();

// Control drawer visibility
const visible = ref(false);

let product
// Expose method to open the modal with a specific product record
defineExpose({
  openModal(record: any = {}) {
    product = {...record};
    visible.value = true;
  },
})
</script>

<style scoped>
/* Carousel section is sticky to stay at the top while scrolling */
.fixed-carousel {
  position: sticky;
  top: 0;
  z-index: 1;
  background: #fff;
}

/* Scrollable content for detailed product information */
.scrollable-content {
  overflow-y: auto;
  margin-top: 16px;
}

/* Styling for additional product details */
.product-detail {
  margin-top: 16px;
}
</style>
