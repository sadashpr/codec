package com.codec.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FileHashServiceTest
{
    @Test
    public void testwriteandread()
    {
        FileHashService.writeHash("abcd");
        boolean expectfail = FileHashService.checkHash("abcd");
        
        assertEquals(expectfail,false);
    }

    @Test
    public void testwriteandread2()
    {
        FileHashService.writeHash("abcd");
        boolean expectTrue = FileHashService.checkHash("XYZ");
        assertEquals(expectTrue,true);
    }

    @Test
    public void testGetHash()
    {
        String hash = FileHashService.getHash("abcd");
        System.out.println(hash);
        assertEquals(hash ,"MD5=OPEN-FAILED: No such file or directory: *abcd");
    }

    // @Test   disable comment to run this test. But needs a valid path varaible like shown below.
    public void testGetHash2()
    {
        String hash = FileHashService.getHash("/Users/pradeep.sadashiv/Documents/test/assignment_files/files/all_english.mkv");
        System.out.println(hash);
        assertEquals(hash ,"MD5=0x96b796c2932f99e8c715241612e6967f */Users/pradeep.sadashiv/Documents/test/assignment_files/files/all_english.mkv");
    }
}