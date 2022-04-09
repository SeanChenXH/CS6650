package repository;

public interface IGenericRepository<T> {

  void addDataForUniqueSkiersVisitedOnDayAndResort(T item);
  void addDataForLiftRidesOnDayAndLift(T item);
  void addDataForLiftRidesOnDayAndHour(T item);

}
