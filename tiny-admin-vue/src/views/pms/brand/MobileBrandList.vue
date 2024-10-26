<script lang="ts" setup>
import {Pagination} from "ant-design-vue";
import {DeleteOutlined, EditOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {deleteBrandById, saveOrUpdateBrand} from "@/api/pms/brand.ts";

const props: any = defineProps({
  dataSource: Array,
  pagination: Pagination,
  isLoading: Boolean
})
const emit = defineEmits(['openModal', 'queryList'])
const confirmChangeStatus = (record: any) => {
  record.status = !record.status
  saveOrUpdateBrand(record).then(() => {
    emit('queryList')
  })
}
const deleteBrand = (id: number) => {
  deleteBrandById(id).then(() => {
    emit('queryList')
  })
}
const openModal = (record: any) => {
  emit('openModal', record)
}
const handlePageChange = (pageNum: number, pageSize: number) => {
  console.log(pageNum, pageSize)
  emit('queryList', {pageNum, pageSize})
}
</script>

<template>
  <a-list :data-source="props.dataSource" :loading="props.isLoading"
          :pagination="{...props.pagination, onChange: handlePageChange}"
          item-layout="horizontal">
    <template #renderItem="{ item:record }">
      <a-list-item>
        <a-list-item-meta>
          <template #description>
            <a-typography-paragraph
                :content="record.brandDesc"
                :ellipsis="{ rows: 3, expandable: true, symbol: 'more' }"
            />
          </template>
          <template #title>
            <div class="flex justify-between items-center">
              <a-tooltip :title="record.brandStory" placement="bottomLeft">
                <a-button size="small" type="link">{{ record.brandName }}</a-button>
              </a-tooltip>
              <div class="flex justify-start items-center">
                <a-popconfirm
                    :cancel-text="$t('否')"
                    :ok-text="$t('是')"
                    :title="
                record.status ? $t('是否禁用该品牌？') : $t(' 是否启用该品牌？')
              "
                    @confirm="
                () => {
                  confirmChangeStatus(record)
                }
              "
                >
                  <template #icon>
                    <question-circle-outlined style="color: red"/>
                  </template>
                  <a-switch
                      v-model:checked="record.status"
                      :checked-children="$t('已启用')"
                      :loading="record.loading"
                      :un-checked-children="$t('已禁用')"
                      class="flex-shrink-0"
                      size="small"
                      @click="record.status=!record.status"
                  />
                </a-popconfirm>
                <a-popconfirm
                    :cancel-text="$t('否')"
                    :ok-text="$t('是')"
                    ok-type="danger"
                    @confirm="deleteBrand(record.id)"
                >
                  <template #icon>
                    <question-circle-outlined style="color: red"/>
                  </template>
                  <template #title>
                    <div>{{ $t('是否删除品牌？') }}</div>
                    <a-tag class="my-2" color="red">{{
                        record.brandName
                      }}
                    </a-tag>
                  </template>
                  <a-button class="flex items-center ml-2" danger type="link">
                    <DeleteOutlined/>
                  </a-button>
                </a-popconfirm>
                <a-button class="flex items-center" type="link" @click="() => openModal(record)">
                  <EditOutlined/>
                </a-button>
              </div>
            </div>
          </template>
          <template #avatar>
            <div>
              <a-avatar :src="record.logo" size="large"/>
            </div>
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>