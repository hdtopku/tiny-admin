import {getCurrentInstance} from 'vue'

export default (): any => {
    const instance = getCurrentInstance();

    if (instance) {
        const {appContext: {app: {config: {globalProperties}}}} = instance;
        return {...globalProperties};
    }

    return {};
}