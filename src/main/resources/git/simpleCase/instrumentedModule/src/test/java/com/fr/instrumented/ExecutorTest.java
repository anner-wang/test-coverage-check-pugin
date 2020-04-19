package com.fr.instrumented;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Executor.class, Register.class})
public class ExecutorTest {

    @Test
    public void testExecute() {

        Register builder = null;

        try {
            builder = PowerMock.createMock(Register.class);
            PowerMock.expectNew(Register.class)
                    .andReturn(builder)
                    .anyTimes();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertNotNull(builder);

        EasyMock.expect(builder.push(EasyMock.anyString())).andReturn(builder).anyTimes();

        EasyMock.expect(builder.pop()).andReturn("not-a-a-a").anyTimes();

        PowerMock.replay(builder, Register.class);
        String value = new Executor().execute("a-template-a");
        Assert.assertEquals("not-a-a-a", value);
        PowerMock.verify(builder);
        PowerMock.verify(Register.class);
    }
}