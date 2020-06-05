package org.springframework.data.ssdb.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.ssdb.client.SSDBCommand.CS;

public class SSDBResponse {
    public String status;
    public List<byte[]> raw;
    /**
     * Indicates items' order
     */
    public List<byte[]> keys = new ArrayList<byte[]>();
    /**
     * key-value results
     */
    public Map<byte[], byte[]> items = new LinkedHashMap<byte[], byte[]>();

    public SSDBResponse(List<byte[]> raw) {
        this.raw = raw;
        if (raw.size() > 0) {
            status = new String(raw.get(0));
        }
    }

    public void exception() {
        if (raw.size() >= 2) {
            throw new SSDBException(new String(raw.get(1)));
        } else {
            throw new SSDBException("no description in " + raw);
        }
    }

    public boolean ok() {
        return status.equals("ok");
    }

    public boolean not_found() {
        return status.equals("not_found");
    }

    public void buildMap() {
        for (int i = 1; i + 1 < raw.size(); i += 2) {
            byte[] k = raw.get(i);
            byte[] v = raw.get(i + 1);
            keys.add(k);
            items.put(k, v);
        }
    }

    public Map<String, String> getAsHashMap() {
        if (not_found()) {
            return null;
        }
        if (!ok()) {
            exception();
        }
        buildMap();
        return items.entrySet().stream()
                .collect(Collectors.toMap(it -> {
                    byte[] key = it.getKey();
                    return new String(key, CS);
                }, it -> {
                    byte[] key = it.getValue();
                    return new String(key, CS);
                }));
    }

    public Map<String, Long> getAsZset() {
        if (not_found()) {
            return null;
        }
        if (!ok()) {
            exception();
        }

        buildMap();
        return items.entrySet().stream()
                .collect(Collectors.toMap(it -> {
                    byte[] key = it.getKey();
                    return new String(key, CS);
                }, it -> {
                    byte[] key = it.getValue();
                    return Long.valueOf(new String(key, CS));
                }));
    }

    public List<String> getAsList() {
        if (not_found()) {
            return null;
        }
        if (!ok()) {
            exception();
        }
        buildMap();
        return items.values().stream().map(it -> new String(it, CS))
                .collect(Collectors.toList());
    }

    public String getAsString() {
        if (not_found()) {
            return null;
        }
        if (raw.size() != 2) {
            throw new SSDBException("Invalid response");
        }
        if (ok()) {
            return new String(raw.get(1), CS);
        }
        exception();
        return null;
    }

    public Long getAsLong() {
        if (not_found()) {
            return null;
        }
        if (raw.size() != 2) {
            throw new SSDBException("Invalid response");
        }
        if (ok()) {
            return Long.valueOf(new String(raw.get(1), SSDBCommand.CS));
        }
        exception();
        // never happen since we throw a exception
        return null;
    }

    public boolean getAsBoolean() {
        if (not_found()) {
            return false;
        }
        if (raw.size() != 2) {
            throw new SSDBException("Invalid response");
        }
        if (ok()) {
            return Long.valueOf(new String(raw.get(1), SSDBCommand.CS)) != 0;
        }
        exception();
        // never happen since we throw a exception
        return false;
    }

    public void getAsVoid() {
        if (ok()) {
            return;
        }
        exception();
    }

    public void print() {
        System.out.println(String.format("%-15s %s", "key", "value"));
        System.out.println("---------------------");
        System.out.println(keys.stream()
                .map(String::new)
                .collect(Collectors.joining("\n")));
        System.out.println("---------------------");
        System.out.println(items.entrySet().stream()
                .map(it -> new String(it.getKey()) + ":\t" + new String(it.getValue())
                ).collect(Collectors.joining("\n")));

        System.out.println("---------------------");
        for (byte[] bs : keys) {
            System.out.print(String.format("%-15s", SSDBMemoryStream.repr(bs)));
            System.out.print(": ");
            System.out.print(SSDBMemoryStream.repr(items.get(bs)));
            System.out.println();
        }
    }
}
