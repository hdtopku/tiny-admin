<script lang="ts" setup>
import {DeleteOutlined, EditOutlined, ExpandAltOutlined, QuestionCircleOutlined} from '@ant-design/icons-vue'
import {Pagination} from "ant-design-vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import {deleteGoodsById, saveOrUpdateGoods} from "@/api/pms/goods.ts";

const props: any = defineProps({
  dataSource: Array,
  pagination: Pagination,
})

const emit = defineEmits(['openModal', 'queryList'])
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
</script>

<template>
  <a-card size="small">
    <div class="grid grid-cols-1 gap-4">
      <a-card v-for="(record, index) in props.dataSource" :key="index" class="shadow-md">
        <template #cover>
          <div class="bg-gray-100 dark:bg-gray-800 w-full h-48">
            <ImageCarousel :img-urls="record?.albumList || []" class="mx-auto"/>
          </div>
        </template>
        <template #actions>
          <a-button type="link">
            <ExpandAltOutlined/>
          </a-button>
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
          <a-button type="link" @click="() => openModal(record)">
            <EditOutlined/>
          </a-button>
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
              <a-tag class="my-2" color="red">{{
                  record.goodsName
                }}
              </a-tag>
            </template>
            <a-button danger type="link">
              <DeleteOutlined/>
            </a-button>
          </a-popconfirm>
        </template>
        <a-card-meta :description="record.goodsDesc" :title="record.goodsName">
        </a-card-meta>
      </a-card>
    </div>
  </a-card>
</template>

