package io.druid.client.selector;

import com.google.api.client.util.Maps;
import com.google.common.primitives.Ints;
import com.google.inject.Inject;

import java.util.Comparator;
import java.util.Map;

/**
 */
public class CustomTierSelectorStrategy extends AbstractTierSelectorStrategy
{
  private final Comparator<Integer> comparator;

  @Inject
  public CustomTierSelectorStrategy(
      ServerSelectorStrategy serverSelectorStrategy,
      CustomTierSelectorStrategyConfig config
  )
  {
    super(serverSelectorStrategy);

    final Map<Integer, Integer> lookup = Maps.newHashMap();
    int pos = 0;
    for (Integer integer : config.getPriorities()) {
      lookup.put(integer, pos);
      pos++;
    }

    this.comparator = new Comparator<Integer>()
    {
      @Override
      public int compare(Integer o1, Integer o2)
      {
        int pos1 = lookup.get(o1);
        int pos2 = lookup.get(o2);
        return Ints.compare(pos1, pos2);
      }
    };
  }

  @Override
  public Comparator<Integer> getComparator()
  {
    return comparator;
  }
}
