<script lang="ts" setup>
import {
  DeleteOutlined,
  EditOutlined,
  ExpandAltOutlined,
  QuestionCircleOutlined,
  SyncOutlined,
  VerticalAlignBottomOutlined,
  VerticalAlignTopOutlined
} from '@ant-design/icons-vue'
import {Pagination} from "ant-design-vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import {deleteGoodsById, saveOrUpdateGoods} from "@/api/pms/goods.ts";

const props: any = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loadingMore: Boolean
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
const deleteGoods = (id: string) => {
  deleteGoodsById(id)
}
const scrollToBottom = () => {
// 获取到所有的 Item 项
  const bottom = document.querySelector('#bottom')
// 调用 scrollIntoView() 方法，显示这个元素
  bottom && bottom.scrollIntoView({
    behavior: 'smooth'
  })
}
const loadMore = () => {
  emit('loadMore')
}
const reload = () => {
  location.reload()
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
              <question-circle-outlined style="color: red"/>
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
              @confirm="deleteGoods(record.id)"
          >
            <template #icon>
              <question-circle-outlined style="color: red"/>
            </template>
            <template #title>
              <div>{{ $t('是否删除商品？') }}</div>
              {{
                record.goodsName
              }}
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
      <div id="bottom" class="text-center pb-4">
        <a-button v-if="props.pagination.current < props.pagination.total / props.pagination.pageSize"
                  :loading="props.loadingMore"
                  block ghost type="primary" @click="loadMore">
          {{ props.loadingMore ? '加载中...' : '加载更多' }}
        </a-button>
        <a-divider v-else>
          {{ props.loadingMore ? '加载中...' : '没有更多数据了' }}
        </a-divider>
      </div>
    </div>
    <a-float-button-group :style="{ right: '24px' }" shape="square">
      <a-float-button class="group" @click="reload">
        <template #icon>
          <SyncOutlined class="group-hover:scale-110 duration-300"/>
        </template>
      </a-float-button>
      <a-back-top :visibility-height="0" class="group">
        <template #icon>
          <VerticalAlignTopOutlined class="group-hover:scale-110 duration-300"/>
        </template>
      </a-back-top>
      <a-float-button class="group" @click="scrollToBottom">
        <template #icon>
          <VerticalAlignBottomOutlined class="group-hover:scale-110 duration-300"/>
        </template>
      </a-float-button>
    </a-float-button-group>
  </a-card>
</template>

