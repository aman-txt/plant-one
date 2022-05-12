module.exports = {
  lintOnSave: false,
  runtimeCompiler: true,
  productionSourceMap: false,
  css: {
    loaderOptions: {
      sass: {
        data: `@import "@/assets/styles/shared/_shared.scss";`
      }
    }
  },
  configureWebpack:{
    performance: {
      hints: false
    },
    optimization: {
      splitChunks: {
        minSize: 10000,
        maxSize: 800000,
      }
    }
  }
}
