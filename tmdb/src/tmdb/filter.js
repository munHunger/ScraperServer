const movieFilter = (input, movie) => {
  return Object.keys(input.filter)
    .map(key => {
      return Object.keys(input.filter[key])
        .map(comp => {
          let compVal = input.filter[key][comp];
          let field = movie[key];
          if (typeof field === "string") {
            field = field.toUpperCase();
            compVal = compVal.toUpperCase();
          }
          switch (comp) {
            case "eq":
              return field === compVal;
            case "lt":
              return field < compVal;
            case "gt":
              return field > compVal;
            case "in":
              return (
                compVal
                  .map(f => (typeof f === "string" ? f.toUpperCase() : f))
                  .filter(f => field === f).length > 0
              );
            case "contains":
              return field.indexOf(compVal) > -1;
          }
        })
        .reduce((acc, val) => (acc &= val), true);
    })
    .reduce((acc, val) => (acc &= val), true);
};
module.exports = {
  movieFilter
};
