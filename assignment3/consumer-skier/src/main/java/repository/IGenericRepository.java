package repository;

public interface IGenericRepository<T> {

  void addDataForDaysOnSkierAndSeason(T item);
  void addDataForVerticalTotalsOnSkierAndDay(T item);
  void addDataForLiftsOnSkierAndDay(T item);

}
