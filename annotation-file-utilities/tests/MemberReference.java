import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

@Target(ElementType.TYPE_USE) @interface A {}
@Target(ElementType.TYPE_USE) @interface B {}
@Target(ElementType.TYPE_USE) @interface C {}
@Target(ElementType.TYPE_USE) @interface D {}

public class MemberReference<T> {
  class Inner<U> implements Map.Entry<T, U> {
    @Override
    public T getKey() { return null; }
    @Override
    public U getValue() { return null; }
    @Override
    public U setValue(U u) { throw new UnsupportedOperationException(); }
  }

  boolean test = false;
  List list = new ArrayList<T>();
  Collection<T>[] foo;

  <U extends T> MemberReference(U bar) {
    foo = new ArrayList[1];
    foo[0] = new ArrayList<T>(1);
    foo[0].add(bar);
  }

  // instance method
  ToIntFunction<String> o1 = String::length;

  // static method
  LongSupplier o2 = System::currentTimeMillis;

  // explicit type args
  ToIntFunction<List<String>> o3 = List<String>::size;

  // inferred type args
  ToIntFunction<List<String>> o4 = List::size;

  UnaryOperator<Integer[]> o5 = Integer[]::clone;

  Function<T, String> o6 = T::toString;

  //IntSupplier o7 = ((String) "abc")::length;
  //IntSupplier o8 = ((List<String>) foo[0])::size;

  Supplier<Iterator<T>> o9 =
      (test ? list : Collections.<T>emptyList()) :: iterator;

  // constructor for parameterized type
  Supplier<ArrayList<String>> o10 =
      ArrayList<String>::new;

  // inferred type arguments for generic class
  Supplier<ArrayList<String>> o11 =
      ArrayList::new;

  // explicit type arguments for generic constructor
  IntFunction<List<Integer>> o12 =
      ArrayList<Integer>::<Integer>new;

  // generic class + constructor
  Function<String, MemberReference<CharSequence>> o13 =
      MemberReference<CharSequence>::<String>new;

  // inner class constructor
  Supplier<MemberReference<Object>.Inner<Long>> o14 =
      MemberReference<Object>.Inner<Long>::<String>new;

  // array creation
  Function<Integer, Integer[]> o15 =
      Integer[]::new;

  //Consumer<String> o16 = System.out::println;
  //Supplier<String> o17 = ((Object) super)::toString;
  //Supplier<String> o18 = ((Object) MemberReference.super)::toString;

  public static void main(String[] args) {
    String[] ss = {"a", "b"};
    System.out.println(java.util.Arrays.asList(ss).stream()
        .map(String::toUpperCase)
        .collect(java.util.stream.Collectors.joining(" ")));
  }
}

