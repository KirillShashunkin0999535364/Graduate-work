
module.exports = {
  entry: { main: "./src/index.tsx" },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: "ts-loader",
        exclude: /node_modules/,
      },
      { test: /\.xml$/, loader: "xml-loader" },
      {
        test: /\.(png|jpe?g|gif|jp2|webp)$/,
        loader: "file-loader",
        options: {
          name: "[name].[ext]",
        },
      },
    ],
  },
  resolve: {
    extensions: [".tsx", ".ts", ".js"],
  },
};
