<script setup lang="ts">

const props = defineProps({
  searchForm: {
    type: Object,
    default: () => ({
      keyword: '',
      status: true
    })
  }
})
const loading = ref(false)
const saveOrUpdateUserInfo = () => {

}
const queryList = () => {

}
const queryByStatus = () => {

}
</script>

<template>
  <div class="flex mb-4">
    <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
      <a-button type="primary" @click="saveOrUpdateUserInfo">{{
          $t('新增')
        }}
      </a-button>
      <a-input
          id="keyword"
          v-model:value="props.searchForm.keyword"
          :placeholder="$t('搜索用户名、昵称、邮箱、手机号')"
          allow-clear
          autocomplete="off"
          class="text-left"
          type="text"
          @keyup.enter.native="queryList"
      >
        <template #prefix>
          <a-switch
              v-model:checked="props.searchForm.status"
              :checked-children="$t('已启用')"
              :loading="loading"
              :un-checked-children="$t('已禁用')"
              class="flex-shrink-0"
              @change="queryByStatus"
          />
        </template>
        <template #suffix>
          <a-button type="primary" @click="queryList">{{
              $t('搜索')
            }}
          </a-button>
        </template>
      </a-input>
    </div>
  </div>
</template>

<style scoped>

</style>