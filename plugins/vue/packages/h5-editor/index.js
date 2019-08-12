import H5Editor from './src/main'

H5Editor.install = function (Vue, options) {
    Vue.component(H5Editor.name, H5Editor);
}

export default H5Editor;