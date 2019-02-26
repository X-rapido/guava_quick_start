package com.example.guava.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import org.junit.Test;

public class HashingTest {

    @Test
    public void test() {
        String input = "hello, world";
        // 计算MD5
        System.out.println(Hashing.md5().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.md5().hashUnencodedChars(input).toString());

        System.out.println(Hashing.farmHashFingerprint64().hashBytes(input.getBytes()));
        System.out.println(Hashing.farmHashFingerprint64().hashUnencodedChars(input).toString());

        // 计算sha256
        System.out.println(Hashing.sha256().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.sha256().hashUnencodedChars(input).toString());

        // 计算sha512
        System.out.println(Hashing.sha512().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.sha512().hashUnencodedChars(input).toString());

        // 计算crc32
        System.out.println(Hashing.crc32().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.crc32().hashUnencodedChars(input).toString());

        // 计算adler32
        System.out.println(Hashing.adler32().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.adler32().hashUnencodedChars(input).toString());

        // 计算crc32c
        System.out.println(Hashing.crc32c().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.crc32c().hashUnencodedChars(input).toString());

        // 计算murmur3_32
        System.out.println(Hashing.murmur3_32().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.murmur3_32().hashUnencodedChars(input).toString());

        // 计算murmur3_128
        System.out.println(Hashing.murmur3_128().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.murmur3_128().hashUnencodedChars(input).toString());

        // 计算sha384
        System.out.println(Hashing.sha384().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.sha384().hashUnencodedChars(input).toString());

        // 计算sipHash24
        System.out.println(Hashing.sipHash24().hashBytes(input.getBytes()).toString());
        System.out.println(Hashing.sipHash24().hashUnencodedChars(input).toString());
    }

    @Test
    public void test2() {
//        HashFunction function_0 = Hashing.md5();
//        HashFunction function_1 = Hashing.md5();
        HashFunction function_0 = Hashing.murmur3_128();
        HashFunction function_1 = Hashing.murmur3_128();

        Person person = new Person();
        person.setAge(27);
        person.setName("hahahah");
        person.setAddress("北京三里屯");
        person.setPhoneNumber(16666666666L);
        person.setMale(Male.man);

        HashCode code_0 = function_0.newHasher()
                .putInt(person.getAge())
                .putString(person.getName(), Charsets.UTF_8)
                .putString(person.getAddress(), Charsets.UTF_8)
                .putLong(person.getPhoneNumber())
                .putObject(person.getMale(), new Funnel<Male>() {
                    @Override
                    public void funnel(Male from, PrimitiveSink into) {
                        into.putString(from.name(), Charsets.UTF_8);
                    }
                }).hash();

        HashCode code_1 = function_1.newHasher()
                .putInt(person.getAge())
                .putString(person.getName(), Charsets.UTF_8)
                .putString(person.getAddress(), Charsets.UTF_8)
                .putLong(person.getPhoneNumber())
                .putObject(person.getMale(), new Funnel<Male>() {
                    @Override
                    public void funnel(Male from, PrimitiveSink into) {
                        into.putString(from.name(), Charsets.UTF_8);
                    }
                }).hash();

        System.out.println(code_0.asLong());
        System.out.println(code_1.asLong());

        System.out.println(code_0.equals(code_1));
    }
}
