module.exports = {
  lintOnSave: false,
  outputDir: '../src/main/resources/static',
  devServer: {
    // port: 8081,
    proxy: {
      '/': {
        "target": 'http://localhost:8080/pipeline',
        // "pathRewrite":{'^/pipeline':''},
        "changeOrigin":true,
        "secure":false
      }
    }
  }
}
