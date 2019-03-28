<template>
    <div class="question">
        <div class="label">{{'问题'+seq}}：</div>
        <div class="input">
            <input :placeholder="placeholder" :value="value" @input="input" />
        </div>
    </div>
</template>

<script>
    export default {
        name: "Question",
        props: {
            seq: Number,
            placeholder: String,
            value: String
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        methods: {
            input(e){
                this.$emit('change', e.target.value);
            }
        }
    }
</script>

<style scoped lang="less">
    @labelWidth: 60px;

    .question {
        height: 36px;
        line-height: 36px;
        display: flex;
        margin-bottom: 10px;
        .label {
            width: @labelWidth;
        }
        .input{
            flex: 1;
            input {
                border: 0;
                padding: 0 10px;
                width: 100%;
                &:hover {
                    border: 1px solid #ff4d4f;
                }
            }
        }
    }
</style>