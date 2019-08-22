<template>
    <div>
        <a-form>
            <a-form-item label="名称">
                <a-input v-model="resource.name"></a-input>
            </a-form-item>
            <a-form-item label="路径">
                <a-input v-model="resource.path"></a-input>
            </a-form-item>
            <a-form-item label="请求类型">
                <a-input v-model="resource.type"></a-input>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import {createNamespacedHelpers} from 'vuex'
    const resourceStore = createNamespacedHelpers("resource");
    export default {
        name: "Edit",
        data(){
            return {

            }
        },
        computed: {
            ...resourceStore.mapState({
                resource: state => state.editResource
            })
        }
    }
</script>

<style scoped>

</style>