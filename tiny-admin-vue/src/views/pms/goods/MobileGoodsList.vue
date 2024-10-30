<script lang="ts" setup>
import {DeleteOutlined, EditOutlined, ExpandAltOutlined, QuestionCircleOutlined,} from '@ant-design/icons-vue'
import {Pagination} from "ant-design-vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import {deleteGoodsById, saveOrUpdateGoods} from "@/api/pms/goods.ts";

const props: any = defineProps({
  dataSource: Array,
  pagination: Pagination,
  isLoading: Boolean
})
const emit = defineEmits(['openModal', 'queryList', 'loadMore'])
const confirmChangeStatus = (record: any) => {
  record.status = !record.status
  saveOrUpdateGoods(record).then(() => {
    emit('queryList')
  })
}
const openModal = (record: any) => {
  emit('openModal', record)
}
const deleteRecord = (id: string) => {
  deleteGoodsById(id)
}
const loadMore = () => {
  emit('loadMore')
}
</script>

<template>
  <a-card class="relative" size="small">
    <div class="flex flex-col gap-4">
      <a-card v-for="(record, index) in props.dataSource" :key="index" class="shadow-md">
        <template #cover>
          <div class="bg-gray-100 dark:bg-gray-800 h-48">
            <ImageCarousel :img-urls="record?.albumList || []" class="mx-auto"/>
          </div>
        </template>
        <template #actions>
          <a-popconfirm
              :cancel-text="$t('否')"
              :ok-text="$t('是')"
              :title="
                record.status ? $t('是否禁用该品牌？') : $t(' 是否启用该品牌？')
              "
              @confirm="confirmChangeStatus(record)"
          >
            <template #icon>
              <QuestionCircleOutlined style="color: red"/>
            </template>
            <a-switch
                v-model:checked="record.status"
                :checked-children="$t('已启用')"
                :loading="record.loading"
                :un-checked-children="$t('已禁用')"
                class="!mt-2"
                @click="() => {record.status =!record.status}"
            />
          </a-popconfirm>
          <a-popconfirm
              :cancel-text="$t('否')"
              :ok-text="$t('是')"
              ok-type="danger"
              @confirm="deleteRecord(record.id)"
          >
            <template #icon>
              <QuestionCircleOutlined style="color: red"/>
            </template>
            <template #title>
              <div>{{ $t('是否删除商品？') }}</div>
              <a-tag class="my-2" color="red">{{
                  record.goodsName
                }}
              </a-tag>
            </template>
            <a-button danger type="link">
              <DeleteOutlined/>
            </a-button>
          </a-popconfirm>
          <a-button type="link">
            <ExpandAltOutlined/>
          </a-button>
          <a-button type="link" @click="() => openModal(record)">
            <EditOutlined/>
          </a-button>
        </template>
        <a-card-meta :title="record.goodsName">
          <template #description>
            <div>{{ record.goodsDesc }}</div>
            <div class="mt-2">
              <a-tag>市场价</a-tag>
              {{ record.marketPrice }}
              <a-divider type="vertical"/>
              <a-tag>促销价</a-tag>
              {{ record.promotionPrice }}
              <a-divider type="vertical"/>
              <a-tag>库存</a-tag>
              {{ record.stock }}
            </div>

          </template>
        </a-card-meta>
      </a-card>
      <div class="text-center pb-4">
        <a-button v-if="props.pagination.current < props.pagination.total / props.pagination.pageSize"
                  :loading="props.isLoading"
                  block ghost type="primary" @click="loadMore">
          {{ props.isLoading ? '加载中...' : '加载更多' }}
        </a-button>
        <a-divider v-else>
          {{ props.isLoading ? '加载中...' : '没有更多数据了' }}
        </a-divider>
      </div>
    </div>
  </a-card>
</template>

