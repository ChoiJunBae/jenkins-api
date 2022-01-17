module.exports = {
  outputDir: '../src/main/resources/static',
  devServer: {
    port: 9000,
    proxy: {
      '/api': {
        target: 'http://localhost:9001'
      }
    }
  }
}
