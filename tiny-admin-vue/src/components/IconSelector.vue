<template>
  <a-select
      v-model:value="selectValue"
      show-search
      :placeholder="$t('选择图标')"
      style="width: 100px"
      :dropdownMatchSelectWidth="200"
      allow-clear
      autoClearSearchValue
      @search="handleSearch"
      @dropdownVisibleChange="handleOpenChange"
      :open="open"
      :virtual="false"
      :defaultOpen="true"
      mode="multiple"
  >
    <template v-if="fetching" #notFoundContent>
      <a-spin size="small"/>
    </template>
    <template v-else #dropdownRender>
      <div
          class="h-56 overflow-y-auto grid grid-cols-4 items-start content-start overflow-x-hidden"
      >
        <CreateIcon
            v-for="key in options"
            :class="{ 'bg-gray-200': selectValue && selectValue.includes(key) }"
            :icon="key"
            class="flex items-center justify-center h-12 w-12 rounded-md cursor-pointer hover:bg-gray-100"
            @click="() => handleChooseIcon(key)"
        />
      </div>
    </template>
    <template #tagRender="{ value: val }">
      <CreateIcon :icon="val" class="mx-2" @click="() => (open = true)"/>
    </template>
    <template #clearIcon>
      <CreateIcon
          icon="CloseCircleOutlined"
          @click="() => emit('update:value', '')"
      />
    </template>
  </a-select>
</template>
<script lang="ts" setup>
import * as icons from '@ant-design/icons-vue'
import {CreateIcon} from './CustomIcon.ts'
import {Ref} from 'vue'

const selectValue: Ref<any> = defineModel()
const iconsKeys = Object.keys(icons).filter(
    (key) => key.charCodeAt(0) < 91 && key.charCodeAt(0) > 64
)
const options = ref(iconsKeys)
const open = ref(false)
const emit = defineEmits(['update:value'])
const handleChooseIcon = (icon: string) => {
  emit('update:value', icon)
  open.value = false
}
const fetching = ref(true)
const handleSearch = (value: string) => {
  value = value.trim()
  options.value = iconsKeys.filter((key) =>
      key.toLocaleLowerCase().includes(value.toLocaleLowerCase())
  )
}
const handleOpenChange = (val) => {
  open.value = val
  if (val) {
    fetching.value = true
    setTimeout(() => {
      fetching.value = false
    }, 80)
  }
}
</script>
